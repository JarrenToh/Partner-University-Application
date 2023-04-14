import NavbarComp from '../student/components/NavbarComp';
import { AuthContext } from '../../src/AuthContext';
import { useState, useContext, useEffect } from 'react';

function ErrorPage() {
  const { loggedInStudent } = useContext(AuthContext);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  return (
    
    <>
    <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
    <h1 style={{ textAlign: 'center', color: 'red', margin: '0 auto', width: '50%', fontWeight: 'bold', fontSize: '2em'}}>Forum Post has been deleted.</h1>
    </>
  )
}

export default ErrorPage;