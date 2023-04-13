import { useEffect, useState } from "react";
import AccordionComp from "../components/AccordionComp";
import apiPaths from '../../util/apiPaths';
import React from "react";
import { useParams } from 'react-router-dom';
import _ from 'lodash';
import NavbarComp from '../../student/components/NavbarComp';

function MappableModule() {
  const { puName } = useParams();

  const [mappableModules, setMappableModule] = useState([]);

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);

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
    <>
     <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
      <AccordionComp modules={mappableModules} universityName="NUS University" />
      {mappableModules.length === 0
        ? <div
          style={{
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            height: '60vh',
            fontSize: '40px',
          }}
          className="text-muted"
        >
          No mappable modules found.
        </div> : null
      }
    </>
  );
}

export default MappableModule;