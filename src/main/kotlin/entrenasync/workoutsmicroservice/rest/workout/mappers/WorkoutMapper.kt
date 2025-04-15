package entrenasync.workoutsmicroservice.rest.workout.mappers

import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutCreateRequest
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutResponse
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutUpdateRequest
import entrenasync.workoutsmicroservice.rest.workout.models.Workout
import entrenasync.workoutsmicroservice.rest.workoutDetails.mappers.toEntity
import entrenasync.workoutsmicroservice.rest.workoutDetails.mappers.toResponse
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
            workoutDetails = this.workoutDetails!!.toEntity(oldWorkout.workoutDetails),
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
