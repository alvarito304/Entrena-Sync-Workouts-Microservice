package entrenasync.workoutsmicroservice.rest.workout.dtos

import com.example.demo.entity.WorkoutDetails
import entrenasync.workoutsmicroservice.rest.workoutDetails.dtos.WorkoutDetailsUpdateRequest
import jakarta.validation.constraints.*
import kotlin.time.Duration

data class WorkoutUpdateRequest(

    @field:Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters")
    var name: String? = null,

    //this can cause validation problems
    @field:Positive(message = "trainingDuration must be positive")
    var trainingDuration: Long? = null,

    var workoutDetails: WorkoutDetailsUpdateRequest?

)
