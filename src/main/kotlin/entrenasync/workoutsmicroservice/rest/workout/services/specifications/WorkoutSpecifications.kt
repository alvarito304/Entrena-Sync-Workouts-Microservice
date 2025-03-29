package entrenasync.workoutsmicroservice.rest.workout.services.specifications

import entrenasync.workoutsmicroservice.rest.workout.models.Workout
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate
import kotlin.time.Duration

object WorkoutSpecifications {
    fun hasName(name: String?): Specification<Workout>?{
        return name?.let {
                Specification { root, query, criteriaBuilder ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%${it.lowercase()}%")
                }
        }
    }

    fun hasTrainingDuration(duration: Duration?) : Specification<Workout>?{
        return duration?.let {
            Specification { root, query, criteriaBuilder ->
                criteriaBuilder.equal(root.get<Duration>("trainingDuration"), it)
            }
        }
    }

    fun hasTrainingCompletedDate(date: LocalDate?) : Specification<Workout>? {
        return date?.let {
            Specification { root, query, criteriaBuilder ->
                criteriaBuilder.equal(root.get<LocalDate>("trainingCompletedDate"), it)
            }
        }
    }
}