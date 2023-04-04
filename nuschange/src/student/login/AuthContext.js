import { createContext, useContext, useState, useEffect } from "react";

export const AuthContext = createContext();
export const AuthProvider = ({ children }) => {
  const [loggedInStudent, setLoggedInStudent] = useState(null);
  

  useEffect(() => {
    const storedStudent = localStorage.getItem("loggedInStudent");
    if (storedStudent) {
      setLoggedInStudent(JSON.parse(storedStudent));
    }
  }, []);

  const login = (student) => {
    localStorage.setItem("loggedInStudent", JSON.stringify(student));
    setLoggedInStudent(student);
  };

  const logout = () => {
    localStorage.removeItem("loggedInStudent");
    setLoggedInStudent(null);
  };

  return (
    <AuthContext.Provider value={{ loggedInStudent, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  return useContext(AuthContext);
};
