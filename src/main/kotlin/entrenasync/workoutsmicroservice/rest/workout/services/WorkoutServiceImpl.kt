package entrenasync.workoutsmicroservice.rest.workout.services

import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutCreateRequest
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutResponse
import entrenasync.workoutsmicroservice.rest.workout.dtos.WorkoutUpdateRequest
import entrenasync.workoutsmicroservice.rest.workout.exceptions.WorkoutNotFoundException
import entrenasync.workoutsmicroservice.rest.workout.mappers.toEntity
import entrenasync.workoutsmicroservice.rest.workout.mappers.toResponse
import entrenasync.workoutsmicroservice.rest.workout.repositories.IWorkoutRepository
import entrenasync.workoutsmicroservice.rest.workout.services.specifications.WorkoutSpecifications
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.time.Duration

@Service
class WorkoutServiceImpl @Autowired constructor(private val workoutRepository: IWorkoutRepository) : IWorkoutService {

    private val log: Logger = LoggerFactory.getLogger(WorkoutServiceImpl::class.java)

    /*override fun getWorkouts(pageable: Pageable): Page<WorkoutResponse> {
        log.info("Getting all Workouts")
        return workoutRepository.findAll(pageable).map { workout -> workout.toResponse() }
    }*/

    override fun getWorkouts(
        name: String?,
        trainingDuration: Duration?,
        trainingCompletedDate: LocalDate?,
        pageable: Pageable
    ): Page<WorkoutResponse> {
        log.info("Getting all Workouts with specifications")
        val spec = Specification.where(WorkoutSpecifications.hasName(name))
            ?.and(WorkoutSpecifications.hasTrainingDuration(trainingDuration))
            ?.and(WorkoutSpecifications.hasTrainingCompletedDate(trainingCompletedDate))
        return workoutRepository.findAll(spec, pageable).map { it.toResponse() }
    }

    override fun getWorkoutById(id: Long): WorkoutResponse {
        log.info("Getting Workout By Id: $id")
        return workoutRepository.findById(id).orElseThrow{ WorkoutNotFoundException(id) }.toResponse()
    }

    override fun createWorkout(workout: WorkoutCreateRequest): WorkoutResponse {
        log.info("Creating Workout")
        return workoutRepository.save(workout.toEntity()).toResponse()
    }

    override fun updateWorkout(id: Long, workoutToUpdate: WorkoutUpdateRequest): WorkoutResponse {
        log.info("Updating Workout By Id: $id")
        var oldWorkout = workoutRepository.findById(id).orElseThrow { WorkoutNotFoundException(id) }
        var updatedWorkout = workoutToUpdate.toEntity(oldWorkout)
        return workoutRepository.save(updatedWorkout).toResponse()
    }

    override fun deleteWorkout(id: Long) {
        log.info("Deleting Workout By Is: $id")
        var oldWorkout = workoutRepository.findById(id).orElseThrow { WorkoutNotFoundException(id) }
        workoutRepository.delete(oldWorkout)
    }

}