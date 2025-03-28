package entrenasync.workoutsmicroservice.workout.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class WorkoutNotFoundException(id: Long) : WorkoutException("The Workout with id: $id, was not found")
