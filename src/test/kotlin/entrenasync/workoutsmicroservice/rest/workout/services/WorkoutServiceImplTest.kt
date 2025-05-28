package entrenasync.workoutsmicroservice.rest.workout.services

import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutCreateRequest
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutResponse
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutUpdateRequest
import entrenasync.workoutsmicroservice.rest.workout.exceptions.WorkoutNotFoundException
import entrenasync.workoutsmicroservice.rest.workout.mappers.toEntity
import entrenasync.workoutsmicroservice.rest.workout.mappers.toResponse
import kotlin.time.Duration.Companion.seconds
import java.util.Optional
import entrenasync.workoutsmicroservice.rest.workout.models.Workout
import entrenasync.workoutsmicroservice.rest.workout.repositories.IWorkoutRepository
import entrenasync.workoutsmicroservice.rest.workoutDetails.dtos.WorkoutDetailsCreateRequest
import entrenasync.workoutsmicroservice.rest.workoutDetails.dtos.WorkoutDetailsUpdateRequest
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import java.time.LocalDate
import kotlin.time.Duration
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import java.util.*

@ExtendWith(MockKExtension::class)
class WorkoutServiceImplTest {

    @MockK
    lateinit var repository: IWorkoutRepository

    @InjectMockKs
    lateinit var service: WorkoutServiceImpl

    private val sampleEntity = WorkoutCreateRequest(
        name = "Test",
        trainingDuration = 3600L,
        trainingCompletedDate = LocalDate.of(2025, 5, 1),
        workoutDetails = WorkoutDetailsCreateRequest(
            description = "Test description",
            intensity = 5,
            exerciseListId = mapOf("exercise1" to "push-ups", "exercise2" to "squats"),
            additionalDetails = mapOf("note" to "Focus on form", "duration" to "30 minutes")
        ),
        completed = false
    ).toEntity().apply { id = 42L
    workoutDetails.apply { id = 100L } }

    @BeforeEach
    fun setUp() {

    }

    @Test
    fun `getWorkouts sin filtros devuelve todos los workouts`() {
        val pageable = PageRequest.of(0, 10)
        val pageData: Page<Workout> =
            PageImpl(listOf(sampleEntity), pageable, 1)
        every { repository.findAll(pageable) }
            .returns(pageData)

        val result = service.getWorkouts(pageable)

        assertEquals(1, result.content.size)
        assertEquals("Test", result.content[0].name)
        verify { repository.findAll(pageable) }
    }

    @Test
    fun `getWorkouts con todas las especificaciones aplica correctamente los filtros`() {
        // Parámetros de filtro
        val pageable = PageRequest.of(0, 10)
        val idsFilter = listOf(42L, 100L)
        val nameFilter = "Test"
        val durationFilter = 3600.seconds
        val dateFilter = LocalDate.of(2025, 5, 1)
        val completedFilter = false

        // Datos de retorno simulado
        val pageData: Page<Workout> = PageImpl(listOf(sampleEntity), pageable, 1)
        every {
            repository.findAll(any<Specification<Workout>>(), pageable)
        } returns pageData

        // Llamada al servicio
        val result = service.getWorkouts(
            completed = completedFilter,
            ids = idsFilter,
            name = nameFilter,
            trainingDuration = durationFilter,
            trainingCompletedDate = dateFilter,
            pageable = pageable
        )

        // Assertions sobre el resultado
        assertEquals(1, result.content.size)
        val dto = result.content[0]
        assertEquals(sampleEntity.id, dto.id)
        assertEquals(sampleEntity.name, dto.name)
        assertEquals(3600L, dto.trainingDuration)  // compara directamente los segundos
        assertEquals(sampleEntity.trainingCompletedDate, dto.trainingCompletedDate)
        assertEquals(sampleEntity.completed, dto.completed)

        // Verificar que se llamó al repositorio con una Specification no nula y el pageable correcto
        verify(exactly = 1) {
            repository.findAll(match { spec -> spec != null }, pageable)
        }
    }



    @Test
    fun `getWorkoutById devuelve el workout cuando existe`() {
        every { repository.findById(42L) }
            .returns(java.util.Optional.of(sampleEntity))

        val resp: WorkoutResponse = service.getWorkoutById(42L)

        assertEquals(42L, resp.id)
        assertEquals("Test", resp.name)
        verify(exactly = 1) { repository.findById(42L) }
    }

    @Test
    fun `getWorkoutById lanza WorkoutNotFoundException cuando no existe`() {
        every { repository.findById(1L) }
            .returns(java.util.Optional.empty())

        assertThrows<WorkoutNotFoundException> {
            service.getWorkoutById(1L)
        }
        verify { repository.findById(1L) }
    }

    @Test
    fun `createWorkout guarda y retorna el nuevo workout`() {

        val request = WorkoutCreateRequest(
            name = "Nuevo",
            trainingDuration = 3600L,
            trainingCompletedDate = LocalDate.of(2025, 5, 1),
            workoutDetails = WorkoutDetailsCreateRequest(
                description = "Test description",
                intensity = 5,
                exerciseListId = mapOf("exercise1" to "push-ups", "exercise2" to "squats"),
                additionalDetails = mapOf("note" to "Focus on form", "duration" to "30 minutes")
            ),
            completed = false
        )

        val workoutEntity = request.toEntity().apply { id = 100L
        workoutDetails.apply { id = 200L }} 
        System.out.println("Workout Entity: $workoutEntity")

        every { repository.save(any()) } returns workoutEntity

        val result = service.createWorkout(request)

        assertEquals(100L, result.id)
        assertEquals("Nuevo", result.name)
        verify { repository.save(any()) }
    }

    @Test
    fun `updateWorkout actualiza y retorna el workout cuando existe`() {
        // Preparar datos
        val updateRequest = WorkoutUpdateRequest(
            name = "Actualizado",
            trainingDuration = 4500L,
            workoutDetails = WorkoutDetailsUpdateRequest(
                description = "Descripción actualizada",
                intensity = 7,
                exerciseListId = mapOf("exercise1" to "pull-ups"),
                additionalDetails = mapOf("note" to "Mayor intensidad")
            ),
            completed = true
        )

        // Entidad antigua y actualizada
        val oldEntity = sampleEntity // id = 42L
        val updatedEntity = updateRequest.toEntity(oldEntity).apply {
            // los IDs se mantienen
        }

        every { repository.findById(42L) }
            .returns(Optional.of(oldEntity))
        every { repository.save(any()) }
            .returns(updatedEntity)

        // Ejecutar
        val result = service.updateWorkout(42L, updateRequest)

        // Verificar
        assertEquals(42L, result.id)
        assertEquals("Actualizado", result.name)
        assertTrue(result.completed)
        verifyOrder {
            repository.findById(42L)
            repository.save(any())
        }
    }

    @Test
    fun `updateWorkout lanza WorkoutNotFoundException cuando no existe`() {
        every { repository.findById(99L) }
            .returns(Optional.empty())

        assertThrows<WorkoutNotFoundException> {
            service.updateWorkout(99L, mockk())
        }
        verify { repository.findById(99L) }
    }

    @Test
    fun `deleteWorkout elimina el workout cuando existe`() {
        every { repository.findById(42L) }
            .returns(Optional.of(sampleEntity))
        every { repository.delete(sampleEntity) }
            .returns(Unit)

                // No lanza excepción
                service.deleteWorkout(42L)

        verifySequence {
            repository.findById(42L)
            repository.delete(sampleEntity)
        }
    }

    @Test
    fun `deleteWorkout lanza WorkoutNotFoundException cuando no existe`() {
        every { repository.findById(123L) }
            .returns(Optional.empty())

        assertThrows<WorkoutNotFoundException> {
            service.deleteWorkout(123L)
        }
        verify { repository.findById(123L) }
    }


}
