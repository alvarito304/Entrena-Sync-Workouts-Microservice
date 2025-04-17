package entrenasync.workoutsmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication

@SpringBootApplication
@EntityScan(basePackages = [
    "entrenasync.workoutsmicroservice.rest.workout.models",
    "entrenasync.workoutsmicroservice.rest.workoutDetails.models",
    "com.example.demo.entity"
])
class WorkoutsMicroServiceApplication

fun main(args: Array<String>) {
    runApplication<WorkoutsMicroServiceApplication>(*args)
}
