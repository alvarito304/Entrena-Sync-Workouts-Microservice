package entrenasync.workoutsmicroservice.workout.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
abstract class WorkoutException(message: String) : RuntimeException(message)
