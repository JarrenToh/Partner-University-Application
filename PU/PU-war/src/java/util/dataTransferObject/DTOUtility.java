/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.dataTransferObject;

import entity.Enquiry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wjahoward
 */
public class DTOUtility {

    private static EnquiryDTO mapEnquiryToDTO(Enquiry enquiry) {
        EnquiryDTO dto = new EnquiryDTO();
        dto.setEnquiryId(enquiry.getEnquiryId());
        dto.setTitle(enquiry.getTitle());
        dto.setContent(enquiry.getContent());
        dto.setResponse(enquiry.getResponse());
        dto.setStatus(enquiry.getStatus());
        dto.setEnquiryDate(enquiry.getEnquiryDate());
        dto.setResponseStatusDate(enquiry.getResponseStatusDate());
        dto.setStudentFirstName(enquiry.getStudent().getFirstName());
        dto.setStudentLastName(enquiry.getStudent().getLastName());
        return dto;
    }

    public static List<EnquiryDTO> serializeEnquiry(List<Enquiry> enquiries) {
        List<EnquiryDTO> enquiryDTOs = new ArrayList<>();
        for (Enquiry enquiry : enquiries) {
            EnquiryDTO enquiryDTO = mapEnquiryToDTO(enquiry);
            enquiryDTOs.add(enquiryDTO);
        }
        return enquiryDTOs;
    }
    
    public static EnquiryDTO serializeSingleEnquiry(Enquiry enquiry) {
        EnquiryDTO enquiryDTO = mapEnquiryToDTO(enquiry);
        return enquiryDTO;
    }

}
