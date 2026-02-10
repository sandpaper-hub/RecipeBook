import {onDocumentDeleted} from "firebase-functions/v2/firestore";
import * as admin from "firebase-admin";

admin.initializeApp();

export const deleteRecipeSteps = onDocumentDeleted(
  "users/{userId}/recipes/{recipeId}",
  async (event) => {
    const {userId, recipeId} = event.params;

    const stepsRef = admin.firestore()
      .collection("users")
      .doc(userId)
      .collection("recipes")
      .doc(recipeId)
      .collection("steps");

    const snapshot = await stepsRef.get();
    const batch = admin.firestore().batch();

    snapshot.docs.forEach((doc) => {
      batch.delete(doc.ref);
    });

    await batch.commit();
  }
);
