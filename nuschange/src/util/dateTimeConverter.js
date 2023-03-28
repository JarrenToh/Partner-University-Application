import moment from 'moment';

const convertDateForNicerOutput = (dateStr) => {
    const formattedDate = moment(dateStr).format('DD-MM-YYYY HH:mm');
    return formattedDate;
};

export const DateTimeConverter = {
    convertDateForNicerOutput
};