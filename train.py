# train.py

import pandas as pd
import numpy as np
import pickle
import os

from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn.ensemble import RandomForestClassifier
from sklearn.datasets import load_breast_cancer

# ================= CREATE MODELS FOLDER =================
os.makedirs("models", exist_ok=True)

# ================= SAVE FUNCTION =================
def save(model, name):

    with open(f"models/{name}_model.pkl", "wb") as f:
        pickle.dump(model, f)

# =========================================================
# DIABETES
# =========================================================

cols = [
    "Pregnancies",
    "Glucose",
    "BloodPressure",
    "SkinThickness",
    "Insulin",
    "BMI",
    "DiabetesPedigreeFunction",
    "Age",
    "Outcome"
]

df = pd.read_csv(
    "data/diabetes.csv",
    names=cols
)

X = df.drop("Outcome", axis=1)
y = df["Outcome"]

X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.2,
    random_state=42
)

model = RandomForestClassifier(
    n_estimators=500,
    max_depth=20,
    random_state=42
)

model.fit(X_train, y_train)

pred = model.predict(X_test)

print(
    "Diabetes Accuracy:",
    accuracy_score(y_test, pred)
)

save(model, "diabetes")

# =========================================================
# HEART
# =========================================================

df = pd.read_csv("data/heart.csv")

X = df.drop("target", axis=1)
y = df["target"]

X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.2,
    random_state=42
)

model = RandomForestClassifier(
    n_estimators=500,
    max_depth=20,
    random_state=42
)

model.fit(X_train, y_train)

pred = model.predict(X_test)

print(
    "Heart Accuracy:",
    accuracy_score(y_test, pred)
)

save(model, "heart")

# =========================================================
# LIVER
# =========================================================

df = pd.read_csv("data/liver.csv")

df["Gender"] = df["Gender"].map({
    "Male": 1,
    "Female": 0
})

X = df.drop("Dataset", axis=1)
y = df["Dataset"]

y = y.replace(2, 0)

X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.2,
    random_state=42
)

model = RandomForestClassifier(
    n_estimators=500,
    max_depth=20,
    random_state=42
)

model.fit(X_train, y_train)

pred = model.predict(X_test)

print(
    "Liver Accuracy:",
    accuracy_score(y_test, pred)
)

save(model, "liver")

# =========================================================
# KIDNEY
# =========================================================

df = pd.read_csv("data/kidney.csv")

# replace missing values
df.replace("?", np.nan, inplace=True)

# convert ALL columns to string
for col in df.columns:

    df[col] = df[col].astype(str)

# encode ALL text columns
for col in df.columns:

    df[col] = pd.factorize(df[col])[0]

X = df.drop("classification", axis=1)
y = df["classification"]

X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.2,
    random_state=42
)

model = RandomForestClassifier(
    n_estimators=500,
    max_depth=20,
    random_state=42
)

model.fit(X_train, y_train)

pred = model.predict(X_test)

print(
    "Kidney Accuracy:",
    accuracy_score(y_test, pred)
)

save(model, "kidney")

# =========================================================
# CANCER
# =========================================================

data = load_breast_cancer()

X = data.data
y = data.target

X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.2,
    random_state=42
)

model = RandomForestClassifier(
    n_estimators=500,
    max_depth=20,
    random_state=42
)

model.fit(X_train, y_train)

pred = model.predict(X_test)

print(
    "Cancer Accuracy:",
    accuracy_score(y_test, pred)
)

save(model, "cancer")

# =========================================================
# DONE
# =========================================================

print("\nALL MODELS TRAINED SUCCESSFULLY")