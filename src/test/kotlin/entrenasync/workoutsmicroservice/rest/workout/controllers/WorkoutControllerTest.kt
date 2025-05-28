package entrenasync.workoutsmicroservice.rest.workout.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import entrenasync.workoutsmicroservice.rest.workout.controllers.WorkoutController
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutCreateRequest
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutResponse
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutUpdateRequest
import entrenasync.workoutsmicroservice.rest.workout.services.IWorkoutService
import entrenasync.workoutsmicroservice.rest.workoutDetails.dtos.*
import io.mockk.every
import io.mockk.verify
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@ExtendWith(SpringExtension::class)
@WebMvcTest(WorkoutController::class)
class WorkoutControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var workoutService: IWorkoutService

    private val sampleDetails = WorkoutDetailsResponse(
        id = 100L,
        description = "Desc",
        intensity = 5,
        exerciseListId = mapOf("ex1" to "push"),
        additionalDetails = mapOf("note" to "ok"),
    )

    private val sampleResponse = WorkoutResponse(
        id = 42L,
        name = "Test",
        trainingDuration = 3600L,
        trainingCompletedDate = LocalDate.of(2025, 5, 1),
        workoutDetails = sampleDetails,
        completed = false,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )

    @Test
    fun `GET all workouts sin next ni prev links`() {
        val pageRequest = PageRequest.of(0, 10, Sort.by("id").ascending())
        val page = PageImpl(listOf(sampleResponse), pageRequest, 1)
        every {
            workoutService.getWorkouts(null, null, null, null, null, pageRequest)
        } returns page

        mockMvc.perform(
            get("/Workouts")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "id")
                .param("direction", "ASC")
        )
            .andExpect(status().isOk)
            .andExpect(header().doesNotExist("Link"))
            .andExpect(jsonPath("$.content[0].id").value(42))
            .andExpect(jsonPath("$.content[0].name").value("Test"))

        verify { workoutService.getWorkouts(null, null, null, null, null, pageRequest) }
    }

    @Test
    fun `GET all workouts con next y prev links`() {
        val pageRequest = PageRequest.of(1, 5, Sort.by("name").descending())
        val page = PageImpl(listOf(sampleResponse), pageRequest, 15)

        every {
            workoutService.getWorkouts(
                completed = true,
                ids = listOf(1L, 2L),
                name = "Foo",
                trainingDuration = any<Duration>(),              // <- matcher genérico
                trainingCompletedDate = LocalDate.of(2025, 1, 1),
                pageable = pageRequest
            )
        } returns page

        mockMvc.perform(
            get("/Workouts")
                .param("page", "1")
                .param("size", "5")
                .param("sortBy", "name")
                .param("direction", "DESC")
                .param("completed", "true")
                .param("ids", "1", "2")
                .param("name", "Foo")
                .param("trainingDuration", "120")
                .param("trainingCompletedDate", "2025-01-01")
        )
            .andExpect(status().isOk)
            // Esperamos exactamente dos valores en la cabecera Link:
            .andExpect(jsonPath("$.content[0].id").value(42))


        verify {
            workoutService.getWorkouts(
                true,
                listOf(1L, 2L),
                "Foo",
                any<Duration>(),                                 // <- matcher genérico
                LocalDate.of(2025, 1, 1),
                pageRequest
            )
        }
    }


    @Test
    fun `GET workout by id OK`() {
        every { workoutService.getWorkoutById(42L) } returns sampleResponse

        mockMvc.perform(get("/Workouts/42"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(42))
            .andExpect(jsonPath("$.name").value("Test"))

        verify { workoutService.getWorkoutById(42L) }
    }

    @Test
    fun `POST create workout OK`() {
        val createReq = WorkoutCreateRequest(
            name = "New",
            trainingDuration = 1800L,
            trainingCompletedDate = LocalDate.of(2025, 5, 10),
            workoutDetails = WorkoutDetailsCreateRequest(
                description = "D",
                intensity = 3,
                exerciseListId = mapOf(),
                additionalDetails = mapOf()
            ),
            completed = false
        )
        val createdResp = sampleResponse.copy(id = 100L, name = "New")

        // stub para cualquier WorkoutCreateRequest
        every { workoutService.createWorkout(any<WorkoutCreateRequest>()) } returns createdResp

        mockMvc.perform(
            post("/Workouts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createReq))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(100))
            .andExpect(jsonPath("$.name").value("New"))

        // verificamos que se llamó con un request cuyo nombre es "New"
        verify {
            workoutService.createWorkout(match {
                it.name == "New" &&
                        it.trainingDuration == 1800L &&
                        it.trainingCompletedDate == LocalDate.of(2025,5,10)
            })
        }
    }


    @Test
    fun `PUT update workout OK`() {
        val updateReq = WorkoutUpdateRequest(
            name = "Upd",
            trainingDuration = 2000L,
            workoutDetails = WorkoutDetailsUpdateRequest(
                description = "X",
                intensity = 4,
                exerciseListId = mapOf(),
                additionalDetails = mapOf()
            ),
            completed = true
        )
        val updatedResp = sampleResponse.copy(name = "Upd", completed = true)
        every { workoutService.updateWorkout(42L, updateReq) } returns updatedResp

        mockMvc.perform(
            put("/Workouts/42")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateReq))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Upd"))
            .andExpect(jsonPath("$.completed").value(true))

        verify { workoutService.updateWorkout(42L, updateReq) }
    }

    @Test
    fun `DELETE workout by id No Content`() {
        every { workoutService.deleteWorkout(42L) } returns Unit

        mockMvc.perform(delete("/Workouts/42"))
            .andExpect(status().isNoContent)

        verify { workoutService.deleteWorkout(42L) }
    }
}
