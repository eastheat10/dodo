package com.nhnacademy.taskapi.service;

import static java.util.stream.Collectors.*;

import com.nhnacademy.taskapi.dto.request.task.CreateTaskRequest;
import com.nhnacademy.taskapi.dto.response.milestone.MilestoneResponse;
import com.nhnacademy.taskapi.dto.response.task.TaskResponse;
import com.nhnacademy.taskapi.entity.Milestone;
import com.nhnacademy.taskapi.entity.Project;
import com.nhnacademy.taskapi.entity.Tag;
import com.nhnacademy.taskapi.entity.Task;
import com.nhnacademy.taskapi.entity.TaskTag;
import com.nhnacademy.taskapi.entity.ThePersonInCharge;
import com.nhnacademy.taskapi.exception.ProjectNotFoundException;
import com.nhnacademy.taskapi.exception.TagNotfoundException;
import com.nhnacademy.taskapi.exception.TaskNotFoundException;
import com.nhnacademy.taskapi.repository.MilestoneRepository;
import com.nhnacademy.taskapi.repository.PersonRepository;
import com.nhnacademy.taskapi.repository.ProjectMembersRepository;
import com.nhnacademy.taskapi.repository.ProjectRepository;
import com.nhnacademy.taskapi.repository.TagRepository;
import com.nhnacademy.taskapi.repository.TaskRepository;
import com.nhnacademy.taskapi.repository.TaskTagRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final MilestoneRepository milestoneRepository;
    private final TagRepository tagRepository;
    private final TaskTagRepository taskTagRepository;
    private final ProjectMembersRepository projectMembersRepository;
    private final PersonRepository personRepository;

    @Transactional
    public void createTask(CreateTaskRequest createRequest) {

        Project project = projectRepository.findById(createRequest.getProjectId())
                                           .orElseThrow(ProjectNotFoundException::new);

        Milestone milestone = milestoneRepository.findById(createRequest.getMilestoneId())
                                                 .orElse(null);

        Task task = new Task(project, milestone, createRequest);

        Task savedTask = taskRepository.save(task);

        // 업무가 생성될 때 함께 요청된 태그 목록
        List<TaskTag> taskTagList = createRequest.getTags()
                                             .stream()
                                             .map(t -> tagRepository.findById(t).orElse(null))
                                             .filter(Objects::nonNull)
                                             .map(tag -> new TaskTag(savedTask, tag))
                                             .collect(toList());

        // 담당자
        List<Long> existMembers = createRequest.getPersons()
                                               .stream()
                                               .filter(projectMembersRepository::existsById_memberId)
                                               .collect(toList());

        // 프로젝트 멤버중 선택된 담당자
        List<ThePersonInCharge> persons = existMembers.stream()
                                                      .map(person ->
                                                          new ThePersonInCharge(savedTask, person))
                                                      .collect(toList());

        if (taskTagList.size() > 0) {
            taskTagRepository.saveAll(taskTagList);
        }

        if (persons.size() > 0) {
            personRepository.saveAll(persons);
        }
    }

    public TaskResponse findTaskByProjectId(Long projectId) {

        Task task = taskRepository.findByProject_Id(projectId)
                                  .orElseThrow(TaskNotFoundException::new);

        Milestone milestone = task.getMilestone();
        MilestoneResponse milestoneResponse = null;

        if (Objects.nonNull(milestone)) {
            milestoneResponse = new MilestoneResponse(milestone);
        }

        // TODO
        return  null;
    }
}
