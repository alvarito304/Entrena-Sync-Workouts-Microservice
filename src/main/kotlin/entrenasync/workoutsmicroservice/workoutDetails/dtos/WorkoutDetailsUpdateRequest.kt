package entrenasync.workoutsmicroservice.workoutDetails.dtos

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class WorkoutDetailsUpdateRequest (
    val description: String? = null,

    // this may cause problems
    @field:Min(value = 0)
    @field:Max(value = 10)
    var intensity: Int? = null,

    val exerciseListId: Map<String, String>? = null,

    val additionalDetails: Map<String, String>? = null
)