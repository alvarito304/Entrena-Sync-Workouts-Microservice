package entrenasync.workoutsmicroservice.workout.dtos

import entrenasync.workoutsmicroservice.workoutDetails.dtos.WorkoutDetailsResponse
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.time.Duration

data class WorkoutResponse(

    var id: Long,

    var name: String,

    var trainingDuration: Duration,

    var trainingCompletedDate: LocalDate,

    var createdAt: LocalDateTime,

    var updatedAt: LocalDateTime,

    var workoutDetails: WorkoutDetailsResponse
)
