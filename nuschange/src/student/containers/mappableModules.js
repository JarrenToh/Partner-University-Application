import { useEffect, useState } from "react";
import AccordionComp from "../components/AccordionComp";
import apiPaths from '../../util/apiPaths';
import React from "react";
import { useParams } from 'react-router-dom';
import _ from 'lodash';

function MappableModule() {
  const { puName } = useParams();

  const [mappableModules, setMappableModule] = useState([]);

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