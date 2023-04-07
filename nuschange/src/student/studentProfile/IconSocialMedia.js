import React, { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faLinkedin,
  faLinkedinIn,
  faTelegram,
  faInstagram,
  faGithub,
  faFacebook,
  faTwitter,
} from "@fortawesome/free-brands-svg-icons";

import { faGlobe } from "@fortawesome/free-solid-svg-icons";

const IconSocialMedia = ({linkType}) => {
  const [iconChoice, setIconChoice] = useState({});
  

  useEffect(() => {
    if (linkType) {
      console.log(linkType);
      setIconChoice(iconSelector(linkType));
    }
  }, [linkType]);

  const iconSelector = (linkType) => {
    const linkTypeString = "" + linkType;
    console.log(linkTypeString);
    if (linkTypeString.includes("github")) {
      return faGithub;
    } else if (linkTypeString.includes("instagram")) {
      return faInstagram;
    } else if (linkTypeString.includes("linkedin")) {
      return faLinkedinIn;
    } else if (linkTypeString.includes("facebook")) {
      return faFacebook;
    } else if (linkTypeString.includes("twitter")) {
      return faTwitter;
    } else if (linkTypeString.includes("telegram")) {
      return faTelegram;
    } else {
      return faGlobe;
    }
  };

  return <FontAwesomeIcon icon={iconChoice} size="xl" />;
};

export default IconSocialMedia;
