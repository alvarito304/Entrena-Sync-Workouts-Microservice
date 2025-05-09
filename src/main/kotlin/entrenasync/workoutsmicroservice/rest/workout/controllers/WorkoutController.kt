package entrenasync.workoutsmicroservice.rest.workout.controllers

import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutCreateRequest
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutResponse
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutUpdateRequest
import entrenasync.workoutsmicroservice.rest.workout.services.IWorkoutService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.domain.Sort.by
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.time.LocalDate
import kotlin.time.Duration
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/Workouts")
class WorkoutController @Autowired constructor(private val workoutService : IWorkoutService) {

    @GetMapping
    fun getAllWorkouts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "id") sortBy: String,
        @RequestParam(defaultValue = "ASC") direction: String,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) trainingDuration: Duration?,
        @RequestParam(required = false) trainingCompletedDate: LocalDate?
    ) : ResponseEntity<Page<WorkoutResponse>>{

        val sort = if (direction.equals("ASC", ignoreCase = true)) {
            Sort.by(sortBy).ascending()
        } else {
            Sort.by(sortBy).descending()
        }
        val pageable = PageRequest.of(page, size, sort)
        val pageResult =  workoutService.getWorkouts(name, trainingDuration, trainingCompletedDate, pageable)

        val uriBuilder = ServletUriComponentsBuilder.fromCurrentRequest()
        val headers = HttpHeaders()

        if (pageResult.hasNext()){
            val nextPageUri = uriBuilder.replaceQueryParam("page", page + 1).build().toUri()
            // Esto sigue la especificación RFC 5988
            headers.add(HttpHeaders.LINK,  "<$nextPageUri>; rel=\"next\" ")
        }

        if (pageResult.hasPrevious()){
            val prevPageUri = uriBuilder.replaceQueryParam("page", page - 1).build().toUri()
            // Esto sigue la especificación RFC 5988
            headers.add(HttpHeaders.LINK, " <$prevPageUri>; rel=\"prev\" ")
        }

        return ResponseEntity(pageResult, headers, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getWorkoutById(@PathVariable id: Long): ResponseEntity<WorkoutResponse> {
        return ResponseEntity.ok(
            workoutService.getWorkoutById(id)
        )
    }

    @PostMapping
    fun createWorkout(@RequestBody @Valid workoutCreateRequest: WorkoutCreateRequest): ResponseEntity<WorkoutResponse>{
        return ResponseEntity.ok(
            workoutService.createWorkout(workoutCreateRequest)
        )
    }

    @PutMapping("/{id}")
    fun updateWorkout(@PathVariable id: Long, @RequestBody @Valid workoutUpdateRequest: WorkoutUpdateRequest): ResponseEntity<WorkoutResponse>{
        return ResponseEntity.ok(
            workoutService.updateWorkout(id, workoutUpdateRequest)
        )
    }

    @DeleteMapping("/{id}")
    fun deleteWorkout(@PathVariable id: Long) : ResponseEntity<Void>{
        workoutService.deleteWorkout(id)
        return ResponseEntity.noContent().build()
    }

}