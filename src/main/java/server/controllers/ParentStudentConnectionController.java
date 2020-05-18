package server.controllers;

import org.springframework.web.bind.annotation.*;
import server.DTOs.ParentStudentConnectionRequestTransport;
import server.DTOs.ParentStudentConnectionTransport;
import server.services.ParentStudentConnectionService;

import java.util.List;

@RestController
@RequestMapping("/ps-relations")
public class ParentStudentConnectionController {

    private ParentStudentConnectionService parentStudentConnectionService;

    public ParentStudentConnectionController(ParentStudentConnectionService parentStudentConnectionService) {
        this.parentStudentConnectionService = parentStudentConnectionService;
    }

    @GetMapping("/requests")
    public List<ParentStudentConnectionRequestTransport> getParentStudentConnectionRequestsBySchool(
            @RequestParam("schoolId") String schoolId) {
        return parentStudentConnectionService.getParentStudentConnectionRequestsBySchool(schoolId);
    }

    @PostMapping("/requests/")
    public void requestParentStudentConnection(@RequestBody ParentStudentConnectionRequestTransport parentStudentConnection) {
        parentStudentConnectionService.createParentStudentConnectionRequest(parentStudentConnection);
    }

    @PostMapping("/connections/")
    public ParentStudentConnectionTransport approveParentStudentConnection(
            @RequestBody ParentStudentConnectionRequestTransport psConnectionRequestTransport) {
        return parentStudentConnectionService.createParentStudentConnection(psConnectionRequestTransport);
    }

    @DeleteMapping("/requests/{psConnectionRequestId}")
    public void deleteParentStudentConnectionRequest(@PathVariable String psConnectionRequestId) {
        parentStudentConnectionService.deleteParentStudentConnectionRequest(psConnectionRequestId);
    }

}
