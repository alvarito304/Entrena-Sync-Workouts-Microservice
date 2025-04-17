package entrenasync.workoutsmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EntityScan(basePackages = [
    "entrenasync.workoutsmicroservice.rest.workout.models",
    "entrenasync.workoutsmicroservice.rest.workoutDetails.models",
    "com.example.demo.entity"
])
@EnableCaching
class WorkoutsMicroServiceApplication

fun main(args: Array<String>) {
    runApplication<WorkoutsMicroServiceApplication>(*args)
}
