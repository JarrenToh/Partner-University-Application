import { createContext, useContext, useState, useEffect } from "react";

export const AuthContext = createContext();
export const AuthProvider = ({ children }) => {
  const [loggedInStudent, setLoggedInStudent] = useState(null);
  const [loggedInAdmin, setLoggedInAdmin] = useState(null);

  useEffect(() => {
    const storedStudent = localStorage.getItem("loggedInStudent");
    if (storedStudent) {
      setLoggedInStudent(JSON.parse(storedStudent));
    }

    const storedAdmin = localStorage.getItem("loggedInAdmin");
    if (storedAdmin) {
      setLoggedInAdmin(JSON.parse(storedAdmin));
    }
  }, []);

  const login = (student) => {
    localStorage.setItem("loggedInStudent", JSON.stringify(student));
    setLoggedInStudent(student);
  };

  const loginAdmin = (admin) => {
    localStorage.setItem("loggedInAdmin", JSON.stringify(admin));
    setLoggedInAdmin(admin);
  };

  const logout = () => {
    localStorage.removeItem("loggedInStudent");
    setLoggedInStudent(null);
  };

  const logoutAdmin = () => {
    localStorage.removeItem("loggedInAdmin");
    setLoggedInAdmin(null);
  };

  return (
    <AuthContext.Provider value={{ loggedInStudent, login, logout, loggedInAdmin, loginAdmin, logoutAdmin }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  return useContext(AuthContext);
};
