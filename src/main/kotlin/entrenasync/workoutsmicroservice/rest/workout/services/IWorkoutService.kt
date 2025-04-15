package entrenasync.workoutsmicroservice.rest.workout.services

import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutCreateRequest
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutResponse
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutUpdateRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate
import kotlin.time.Duration

interface IWorkoutService {
    fun getWorkouts(pageable: Pageable): Page<WorkoutResponse>
    fun getWorkouts(name: String?, trainingDuration: Duration?, trainingCompletedDate: LocalDate?, pageable: Pageable): Page<WorkoutResponse>
    fun getWorkoutById(id: Long) : WorkoutResponse
    fun createWorkout(workout: WorkoutCreateRequest) : WorkoutResponse
    fun updateWorkout(id: Long, workoutToUpdate: WorkoutUpdateRequest): WorkoutResponse
    fun deleteWorkout(id: Long)
}