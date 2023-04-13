/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservices.restful;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author wjahoward
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(webservices.restful.CORSFilter.class);
        resources.add(webservices.restful.admin.EnquiriesResource.class);
        resources.add(webservices.restful.admin.FaqsResource.class);
        resources.add(webservices.restful.admin.ForumTopicsResource.class);
        resources.add(webservices.restful.admin.LikedPUResource.class);
        resources.add(webservices.restful.admin.NUSchangeAdminsResource.class);
        resources.add(webservices.restful.student.CountryResource.class);
        resources.add(webservices.restful.student.EnquiriesResource.class);
        resources.add(webservices.restful.student.FacultyResource.class);
        resources.add(webservices.restful.student.ForumCommentsResource.class);
        resources.add(webservices.restful.student.ForumPostsResource.class);
        resources.add(webservices.restful.student.ForumTopicsResource.class);
        resources.add(webservices.restful.student.NUSModuleResource.class);
        resources.add(webservices.restful.student.PUModuleResource.class);
        resources.add(webservices.restful.student.PUModuleReviewResource.class);
        resources.add(webservices.restful.student.PUResource.class);
        resources.add(webservices.restful.student.PUReviewResource.class);
        resources.add(webservices.restful.student.RegionResource.class);
        resources.add(webservices.restful.student.StudentResource.class);
    }
    
}
