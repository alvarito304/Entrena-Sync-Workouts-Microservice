package entrenasync.workoutsmicroservice.workout.mappers

import entrenasync.workoutsmicroservice.workout.dtos.WorkoutCreateRequest
import entrenasync.workoutsmicroservice.workout.dtos.WorkoutResponse
import entrenasync.workoutsmicroservice.workout.dtos.WorkoutUpdateRequest
import entrenasync.workoutsmicroservice.workout.models.Workout
import entrenasync.workoutsmicroservice.workoutDetails.mappers.toEntity
import entrenasync.workoutsmicroservice.workoutDetails.mappers.toResponse
import java.time.LocalDateTime


    fun WorkoutCreateRequest.toEntity(): Workout {
        return Workout(
            id = null,
            name = this.name,
            trainingDuration = this.trainingDuration,
            trainingCompletedDate = this.trainingCompletedDate,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            workoutDetails = this.workoutDetails.toEntity()
        )
    }

    fun WorkoutUpdateRequest.toEntity(oldWorkout: Workout): Workout {
        return Workout(
            id = oldWorkout.id,
            name = this.name ?: oldWorkout.name,
            trainingDuration = this.trainingDuration ?: oldWorkout.trainingDuration,
            trainingCompletedDate = oldWorkout.trainingCompletedDate,
            createdAt = oldWorkout.createdAt,
            updatedAt = LocalDateTime.now(),
            workoutDetails = oldWorkout.workoutDetails
        )
    }


    fun Workout.toResponse(): WorkoutResponse {
        return WorkoutResponse(
            id = this.id!!,
            name = this.name,
            trainingDuration = this.trainingDuration,
            trainingCompletedDate = this.trainingCompletedDate,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            workoutDetails = this.workoutDetails.toResponse()
        )
    }
