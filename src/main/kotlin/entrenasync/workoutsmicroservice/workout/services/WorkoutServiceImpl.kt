package entrenasync.workoutsmicroservice.workout.services

import entrenasync.workoutsmicroservice.workout.dtos.WorkoutCreateRequest
import entrenasync.workoutsmicroservice.workout.dtos.WorkoutResponse
import entrenasync.workoutsmicroservice.workout.dtos.WorkoutUpdateRequest
import entrenasync.workoutsmicroservice.workout.exceptions.WorkoutNotFoundException
import entrenasync.workoutsmicroservice.workout.mappers.toEntity
import entrenasync.workoutsmicroservice.workout.mappers.toResponse
import entrenasync.workoutsmicroservice.workout.repositories.IWorkoutRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class WorkoutServiceImpl @Autowired constructor(private val WorkoutRepository: IWorkoutRepository) : IWorkoutService {


    override fun getWorkouts(pageable: Pageable): Page<WorkoutResponse> {
        return WorkoutRepository.findAll(pageable).map { workout -> workout.toResponse() }
    }

    override fun getWorkoutById(id: Long): WorkoutResponse {
        return WorkoutRepository.findById(id).orElseThrow{ WorkoutNotFoundException(id) }.toResponse()
    }

    override fun createWorkout(workout: WorkoutCreateRequest): WorkoutResponse {
        return WorkoutRepository.save(workout.toEntity()).toResponse()
    }

    override fun updateWorkout(id: Long, workoutToUpdate: WorkoutUpdateRequest): WorkoutResponse {
        var oldWorkout = WorkoutRepository.findById(id).orElseThrow { WorkoutNotFoundException(id) }
        var updatedWorkout = workoutToUpdate.toEntity(oldWorkout)
        return WorkoutRepository.save(updatedWorkout).toResponse()
    }

    override fun deleteWorkout(id: Long) {
        var oldWorkout = WorkoutRepository.findById(id).orElseThrow { WorkoutNotFoundException(id) }
        WorkoutRepository.delete(oldWorkout)
    }

}