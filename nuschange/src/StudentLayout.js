import React from 'react';
import NavbarComp from './student/components/NavbarComp';

const StudentLayout = ({ children }) => {
  return (
    <>
      <NavbarComp />
      {children}
    </>
  );
};

export default StudentLayout;
