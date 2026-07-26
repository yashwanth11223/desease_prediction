# app.py

import streamlit as st
import numpy as np
import pickle
import json
import os

# ================= PAGE =================
st.set_page_config(
    page_title="AI Health Assistant",
    page_icon="🩺",
    layout="wide"
)

# ================= UI =================
st.markdown("""
<style>

.stApp {
    background: linear-gradient(to right, #0f172a, #1e293b);
    color: white;
}

.main-title {
    font-size: 42px;
    font-weight: bold;
    color: #38bdf8;
    text-align: center;
}

.sub-title {
    text-align: center;
    color: #cbd5e1;
}

.box {
    background-color: #1e293b;
    padding: 20px;
    border-radius: 15px;
}

</style>
""", unsafe_allow_html=True)

# ================= USERS =================
if not os.path.exists("users.json"):

    with open("users.json", "w") as f:
        json.dump({}, f)

with open("users.json", "r") as f:
    users = json.load(f)

# ================= SESSION =================
if "login" not in st.session_state:
    st.session_state.login = False

# ================= LOGIN =================
if not st.session_state.login:

    st.markdown(
        '<p class="main-title">🩺 AI Health Assistant</p>',
        unsafe_allow_html=True
    )

    st.markdown(
        '<p class="sub-title">Disease Prediction System</p>',
        unsafe_allow_html=True
    )

    option = st.selectbox(
        "Choose",
        ["Login", "Register"]
    )

    username = st.text_input("Username")

    password = st.text_input(
        "Password",
        type="password"
    )

    # LOGIN
    if option == "Login":

        if st.button("Login"):

            if username in users and users[username] == password:

                st.session_state.login = True
                st.rerun()

            else:
                st.error("Invalid Username or Password")

    # REGISTER
    else:

        if st.button("Create Account"):

            users[username] = password

            with open("users.json", "w") as f:
                json.dump(users, f)

            st.success("Account Created")

    st.stop()

# ================= LOAD MODELS =================
diabetes_model = pickle.load(open("models/diabetes_model.pkl", "rb"))
heart_model = pickle.load(open("models/heart_model.pkl", "rb"))
liver_model = pickle.load(open("models/liver_model.pkl", "rb"))
kidney_model = pickle.load(open("models/kidney_model.pkl", "rb"))
cancer_model = pickle.load(open("models/cancer_model.pkl", "rb"))

# ================= TITLE =================
st.markdown(
    '<p class="main-title">🩺 AI Health Assistant</p>',
    unsafe_allow_html=True
)

st.markdown(
    '<p class="sub-title">Smart Multi Disease Prediction System</p>',
    unsafe_allow_html=True
)

# ================= INPUT AREA =================
st.markdown('<div class="box">', unsafe_allow_html=True)

col1, col2 = st.columns(2)

with col1:

    age = st.number_input("Age", 1, 120, 25)

    gender = st.selectbox(
        "Gender",
        ["Male", "Female"]
    )

    glucose = st.number_input(
        "Glucose",
        50,
        500,
        90
    )

    bp = st.number_input(
        "Blood Pressure",
        50,
        300,
        80
    )

    bmi = st.number_input(
        "BMI",
        10.0,
        60.0,
        22.0
    )

with col2:

    chol = st.number_input(
        "Cholesterol",
        50,
        600,
        170
    )

    heart_rate = st.number_input(
        "Heart Rate",
        40,
        250,
        72
    )

    bilirubin = st.number_input(
        "Bilirubin",
        0.1,
        10.0,
        0.6
    )

    creatinine = st.number_input(
        "Creatinine",
        0.1,
        20.0,
        1.0
    )

    hemoglobin = st.number_input(
        "Hemoglobin",
        5.0,
        20.0,
        15.0
    )

st.markdown('</div>', unsafe_allow_html=True)

# ================= ANALYZE =================
if st.button(
    "🔍 Analyze Health",
    use_container_width=True
):

    detected = []
    safe = []

    # ================= DIABETES =================
    if glucose > 140 or bmi > 30:

        detected.append("Diabetes")

    else:

        safe.append("Diabetes")

    # ================= HEART =================
    if bp > 140 or chol > 240 or heart_rate > 170:

        detected.append("Heart Disease")

    else:

        safe.append("Heart Disease")

    # ================= LIVER =================
    if bilirubin > 1.2:

        detected.append("Liver Disease")

    else:

        safe.append("Liver Disease")

    # ================= KIDNEY =================
    if creatinine > 1.5 or hemoglobin < 10:

        detected.append("Kidney Disease")

    else:

        safe.append("Kidney Disease")

    # ================= CANCER =================
    if bmi > 35:

        detected.append("Cancer")

    else:

        safe.append("Cancer")

    # ================= OUTPUT =================
    st.subheader("🩺 Health Report")

    if len(detected) == 0:

        st.success("✅ No Disease Detected")

    else:

        st.error("⚠ Possible Diseases")

        for i in detected:
            st.write("•", i)

    st.subheader("✅ Safe Diseases")

    for i in safe:
        st.success(i)

# ================= LOGOUT =================
if st.button("Logout"):

    st.session_state.login = False
    st.rerun()