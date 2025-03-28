package entrenasync.workoutsmicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WorkoutsMicroServiceApplication

fun main(args: Array<String>) {
    runApplication<WorkoutsMicroServiceApplication>(*args)
}
