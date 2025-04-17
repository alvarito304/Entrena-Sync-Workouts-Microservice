package entrenasync.workoutsmicroservice.rest.workout.dtos

import entrenasync.workoutsmicroservice.rest.workoutDetails.dtos.WorkoutDetailsCreateRequest
import jakarta.validation.constraints.*
import java.time.LocalDate
import kotlin.time.Duration

data class WorkoutCreateRequest(
    @field:Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters")
    @field:NotBlank(message = "Client name must not be empty")
    var name: String,


    @field:NotNull(message = "trainingDuration must not be null")
    @field:Positive(message = "trainingDuration must be positive")
    var trainingDuration: Long,


    @field:NotNull(message = "trainingCompletedDate must not be null")
    @field:PastOrPresent(message = "trainingCompletedDate must be past or present")
    var trainingCompletedDate: LocalDate,

    @field:NotNull(message = "workoutDetails can not be null")
    var workoutDetails: WorkoutDetailsCreateRequest
)
