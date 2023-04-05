const convertToEncodedTextForUrl = (input) => {
    const formattedPUName = input.split("-").join(" ").trim();
    const encodedPUName = encodeURIComponent(formattedPUName);

    return encodedPUName;
};

export const EncodedTextConverter = {
    convertToEncodedTextForUrl
};