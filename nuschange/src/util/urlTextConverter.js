const convertToEncodedTextForUrl = (input) => {
    const formattedPUName = input.split("-").join(" ").trim();
    const encodedPUName = encodeURIComponent(formattedPUName);

    return encodedPUName;
};

const convertNameToSlug = (input) => {
    return input.replace(/\s+/g, '-');
};

export {
    convertToEncodedTextForUrl,
    convertNameToSlug
};