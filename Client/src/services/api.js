import axios from "axios";
import dotenv from "dotenv";

dotenv.config({ quiet: true });

const API = axios.create({
    baseURL: process.env.API_URL,
});

export default API;