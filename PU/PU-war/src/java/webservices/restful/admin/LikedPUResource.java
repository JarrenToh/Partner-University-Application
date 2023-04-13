/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful.admin;

import ejb.session.stateless.StudentSessionBeanLocal;
import entity.PU;
import entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.dataTransferObject.LikedPUDTO;

/**
 *
 * @author wjahoward
 */
@Path("/admin/likedPUs")
@RequestScoped
public class LikedPUResource {
        
    @EJB
    private StudentSessionBeanLocal studentSessionBeanLocal;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLikedPUs() {
        List<Student> students = studentSessionBeanLocal.retrieveAllStudents();
        List<LikedPUDTO> likedPUDTOs = new ArrayList<>();
        
        for (Student student : students) {
            
            List<PU> likedPUs = student.getLikedPUs();
            
            if (!likedPUs.isEmpty()) {
                for (PU likedPU : likedPUs) {
                    Long id = likedPU.getPuId();
                    String name = likedPU.getName();
                    
                    LikedPUDTO likedPUDTO = new LikedPUDTO(id, name);
                    likedPUDTOs.add(likedPUDTO);
                }
            }
        }
        
        return Response.status(200).entity(
                likedPUDTOs
        ).build();
    }
    
}
