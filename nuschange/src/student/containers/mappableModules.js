import { useState } from "react";
import AccordionComp from "../components/AccordionComp";
import React from "react";

function MappableModule() {
    const [mappableModule, setMappableModule] = useState([
        {
          "facultyName": "Faculty of Arts and Social Sciences",
          "modules": [
            {
              "nusCode": "GEK1508",
              "nusDescription": "Quantitative Reasoning",
              "puCode": "PU-MOD-01",
              "puDescription": "Introduction to Statistics"
            },
            {
              "nusCode": "GEH1049",
              "nusDescription": "Science, Technology and Society",
              "puCode": "PU-MOD-02",
              "puDescription": "Technology and Society"
            }
          ]
        },
        {
          "facultyName": "Faculty of Engineering",
          "modules": [
            {
              "nusCode": "EE2026",
              "nusDescription": "Digital Design",
              "puCode": "PU-MOD-03",
              "puDescription": "Digital Systems"
            },
            {
              "nusCode": "EE3032",
              "nusDescription": "Communication Systems",
              "puCode": "PU-MOD-04",
              "puDescription": "Wireless Communication"
            }
          ]
        }
      ]
      )
    return (
        <AccordionComp modules={mappableModule} universityName="NUS University"/>
        
    );
}

export default MappableModule;