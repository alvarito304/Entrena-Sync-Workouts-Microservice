package entrenasync.workoutsmicroservice.workoutDetails.dtos

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min


class WorkoutDetailsCreateRequest(

    @field:Min(value = 5, message = "Workout details description must be at least 5 characters")
    @field:Max(value = 255, message = "Workout description must be at most 255 characters")
    val description: String,

    @field:Min(value = 0)
    @field:Max(value = 10)
    var intensity: Int = 0,

    var exerciseListId: Map<String, String> = emptyMap(),

    var additionalDetails: Map<String, String> = emptyMap()

)