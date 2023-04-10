package webservices.restful.student;

import ejb.session.stateless.PUSessionBeanLocal;
import ejb.session.stateless.StudentSessionBeanLocal;
import entity.PU;
import entity.Student;
import error.NoResultException;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.formRequestEntity.LoginRequest;

@Path("student")
@RequestScoped
public class StudentResource {

    @EJB
    private StudentSessionBeanLocal studentSessionLocal;

    @EJB
    private PUSessionBeanLocal pUSessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getAllStudents() {
        return studentSessionLocal.retrieveAllStudents();
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStudent(@QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("phoneNumber") String phoneNumber, @QueryParam("email") String email) {

        if (firstName != null) {
            List<Student> results = studentSessionLocal.searchStudentByFirstName(firstName);
            GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(results) {
            };

            return Response.status(200).entity(
                    entity
            ).build();

        } else if (lastName != null) {
            List<Student> results = studentSessionLocal.searchStudentByLastName(lastName);
            GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(results) {
            };

            return Response.status(200).entity(
                    entity
            ).build();
        } else if (phoneNumber != null) {
            List<Student> results = studentSessionLocal.searchStudentByLastName(phoneNumber);
            GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(results) {
            };

            return Response.status(200).entity(
                    entity
            ).build();
        } else if (email != null) {
            List<Student> results = studentSessionLocal.searchStudentByLastName(email);
            GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(results) {
            };

            return Response.status(200).entity(
                    entity
            ).build();
        } else {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "No query conditions")
                    .build();

            return Response.status(400).entity(exception).build();
        }
    } //end searchStudents

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("id") Long cId) {
        try {
            Student c = studentSessionLocal.getStudent(cId);
            return Response.status(200).entity(
                    c
            ).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end getStudent

    @GET
    @Path("/pu/{puId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentByPU(@PathParam("puId") Long puId) {

        PU pu = pUSessionBeanLocal.retrievePuById(puId);
        List<Student> results = studentSessionLocal.retrieveStudentsByPU(pu);
        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(results) {
        };
        return Response.status(200).entity(
                entity
        ).build();

    } //end getStudent

    @GET
    @Path("/pu/puname/{puName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentByPUName(@PathParam("puName") String puName) {

        PU pu = pUSessionBeanLocal.retrievePuByName(puName);
        List<Student> results = studentSessionLocal.retrieveStudentsByPU(pu);
        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(results) {
        };
        return Response.status(200).entity(
                entity
        ).build();

    } //end getStudent

    @GET
    @Path("/pu/puname/withreview/{puName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentWithReviewByPUName(@PathParam("puName") String puName) {

        PU pu = pUSessionBeanLocal.retrievePuByName(puName);
        List<Student> results = studentSessionLocal.retrieveStudentWithReviewByPU(pu);
        GenericEntity<List<Student>> entity = new GenericEntity<List<Student>>(results) {
        };
        return Response.status(200).entity(
                entity
        ).build();

    } //end getStudent

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Student createStudent(Student s) {
        studentSessionLocal.createStudent(s);
        return s;
    } //end createStudent

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editStudent(@PathParam("id") Long cId, Student c) {
        c.setStudentId(cId);
        try {
            studentSessionLocal.updateStudent(c);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    } //end editStudent

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent(@PathParam("id") Long studentId) {
        try {
            studentSessionLocal.deleteStudent(studentId);
            return Response.status(204).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    } //end deleteStudent

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Student student = studentSessionLocal.login(username, password);

        if (student != null) {
            return Response.status(200).entity(student).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    
    @GET
    @Path("/{studentId}/puEnrolled")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PU getEnrolledPu(@PathParam("studentId") long studentId) {
        try {
            Student student = studentSessionLocal.getStudent(studentId);
            return student.getPuEnrolled();
        } catch (NoResultException ex) {
            return null;
        }
    } 

    @Path("/{studentId}/likedPUs")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addLikedUniversity(@PathParam("studentId") long studentId, PU pu) {
        try {
            Student student = studentSessionLocal.getStudent(studentId);
            student.getLikedPUs().add(pu);
            studentSessionLocal.updateStudent(student);

            pu.getStudentsLiked().add(student);
            pUSessionBeanLocal.updatePU(pu);
            return Response.status(204).build();
        } catch (NoResultException ex) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }

    @Path("/{studentId}/likedPUs/{puId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removedLikedUniversity(@PathParam("studentId") long studentId,@PathParam("puId") long puId) {
        try {
            Student student = studentSessionLocal.getStudent(studentId);
            PU pu = pUSessionBeanLocal.retrievePuById(puId);
            
            student.getLikedPUs().remove(pu);
            studentSessionLocal.updateStudent(student);

            pu.getStudentsLiked().remove(student);
            pUSessionBeanLocal.updatePU(pu);
            return Response.status(204).build();
        } catch (NoResultException ex) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();

            return Response.status(404).entity(exception).build();
        }
    }
}
