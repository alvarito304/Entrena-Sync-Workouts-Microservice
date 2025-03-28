package entrenasync.workoutsmicroservice.workoutDetails.dtos


data class WorkoutDetailsResponse(
    open var id: Long,

    var description: String,

    var intensity: Int = 0,

    val exerciseListId: Map<String, String>,

    val additionalDetails: Map<String, String>,
)