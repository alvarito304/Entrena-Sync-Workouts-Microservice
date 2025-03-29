package entrenasync.workoutsmicroservice.rest.workout.dtos

import jakarta.validation.constraints.*
import kotlin.time.Duration

data class WorkoutUpdateRequest(

    @field:Max(value = 40, message = "Max value for client name is 40")
    var name: String? = null,

    //this can cause validation problems
    @field:Positive(message = "trainingDuration must be positive")
    var trainingDuration: Duration? = null,

)
