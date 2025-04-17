package entrenasync.workoutsmicroservice.rest.workoutDetails.dtos

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size


class WorkoutDetailsCreateRequest(

    @field:Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters")
    val description: String,

    @field:Min(value = 0)
    @field:Max(value = 10)
    var intensity: Int = 0,

    var exerciseListId: Map<String, String> = emptyMap(),

    var additionalDetails: Map<String, String> = emptyMap()

)