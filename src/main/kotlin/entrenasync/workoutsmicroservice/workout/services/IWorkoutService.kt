package entrenasync.workoutsmicroservice.workout.services

import entrenasync.workoutsmicroservice.workout.dtos.WorkoutCreateRequest
import entrenasync.workoutsmicroservice.workout.dtos.WorkoutResponse
import entrenasync.workoutsmicroservice.workout.dtos.WorkoutUpdateRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IWorkoutService {
    fun getWorkouts(pageable: Pageable): Page<WorkoutResponse>
    fun getWorkoutById(id: Long) : WorkoutResponse
    fun createWorkout(workout: WorkoutCreateRequest) : WorkoutResponse
    fun updateWorkout(id: Long, workoutToUpdate: WorkoutUpdateRequest): WorkoutResponse
    fun deleteWorkout(id: Long)
}