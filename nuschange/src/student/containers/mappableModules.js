import { useEffect, useState } from "react";
import AccordionComp from "../components/AccordionComp";
import apiPaths from '../../util/apiPaths';
import React from "react";
import { useParams } from 'react-router-dom';
import _ from 'lodash';

function MappableModule() {
  const { puName } = useParams();
  // const [mappableModule, setMappableModule] = useState([
  //   {
  //     "facultyName": "Faculty of Arts and Social Sciences",
  //     "modules": [
  //       {
  //         "nusCode": "GEK1508",
  //         "nusDescription": "Quantitative Reasoning",
  //         "puCode": "PU-MOD-01",
  //         "puDescription": "Introduction to Statistics"
  //       },
  //       {
  //         "nusCode": "GEH1049",
  //         "nusDescription": "Science, Technology and Society",
  //         "puCode": "PU-MOD-02",
  //         "puDescription": "Technology and Society"
  //       }
  //     ]
  //   },
  //   {
  //     "facultyName": "Faculty of Engineering",
  //     "modules": [
  //       {
  //         "nusCode": "EE2026",
  //         "nusDescription": "Digital Design",
  //         "puCode": "PU-MOD-03",
  //         "puDescription": "Digital Systems"
  //       },
  //       {
  //         "nusCode": "EE3032",
  //         "nusDescription": "Communication Systems",
  //         "puCode": "PU-MOD-04",
  //         "puDescription": "Wireless Communication"
  //       }
  //     ]
  //   }
  // ]
  // )

  const [mappableModules, setMappableModule] = useState([]);


  // const [grouped, setGrouped] = useState({});

  useEffect(() => {
    apiPaths.getMappableModulesByPU(puName)
      .then((res) => res.json())
      .then((m) => {
        const groupedModules = _.groupBy(m.map(([module, faculty]) => ({ ...module, faculty })), 'faculty');
        // console.log(JSON.stringify(groupedModules))
        const faculties = Object.entries(groupedModules).map(([facultyName, modules]) => {
          return {
            facultyName,
            modules
          };
        });

        setMappableModule(faculties)

        // console.log(JSON.stringify(faculties));
      });
  }, [puName])

  return (
    <AccordionComp modules={mappableModules} universityName="NUS University" />
    // <></>
  );
}

export default MappableModule;