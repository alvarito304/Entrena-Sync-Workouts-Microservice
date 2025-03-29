package entrenasync.workoutsmicroservice.rest.workoutDetails.mappers

import com.example.demo.entity.WorkoutDetails
import entrenasync.workoutsmicroservice.rest.workoutDetails.dtos.WorkoutDetailsCreateRequest
import entrenasync.workoutsmicroservice.rest.workoutDetails.dtos.WorkoutDetailsResponse
import entrenasync.workoutsmicroservice.rest.workoutDetails.dtos.WorkoutDetailsUpdateRequest



    fun WorkoutDetailsCreateRequest.toEntity(): WorkoutDetails{
        return WorkoutDetails(
            id = null,
            description = this.description,
            intensity = this.intensity,
            exerciseListId = this.exerciseListId,
            additionalDetails = this.additionalDetails,
        )
    }

    fun WorkoutDetailsUpdateRequest.toEntity(oldWorkoutDetails: WorkoutDetails): WorkoutDetails{
        return WorkoutDetails(
            id = null,
            description = this.description ?: oldWorkoutDetails.description,
            intensity = this.intensity ?: oldWorkoutDetails.intensity,
            exerciseListId = this.exerciseListId ?: oldWorkoutDetails.exerciseListId,
            additionalDetails = this.additionalDetails ?: oldWorkoutDetails.additionalDetails,
        )
    }

    fun WorkoutDetails.toResponse(): WorkoutDetailsResponse {
        return WorkoutDetailsResponse(
            id = this.id!!,
            description = this.description,
            intensity = this.intensity,
            exerciseListId = this.exerciseListId,
            additionalDetails = this.additionalDetails,
        )
    }


