package com.sap.sgs.phosphor.fosstars.tool;

import com.sap.sgs.phosphor.fosstars.model.Confidence;
import com.sap.sgs.phosphor.fosstars.model.Feature;
import com.sap.sgs.phosphor.fosstars.model.Score;
import com.sap.sgs.phosphor.fosstars.model.Value;
import com.sap.sgs.phosphor.fosstars.model.Weight;
import com.sap.sgs.phosphor.fosstars.model.feature.oss.OssFeatures;
import com.sap.sgs.phosphor.fosstars.model.score.oss.CommunityCommitmentScore;
import com.sap.sgs.phosphor.fosstars.model.score.oss.OssSecurityScore;
import com.sap.sgs.phosphor.fosstars.model.score.oss.ProjectActivityScore;
import com.sap.sgs.phosphor.fosstars.model.score.oss.ProjectPopularityScore;
import com.sap.sgs.phosphor.fosstars.model.score.oss.ProjectSecurityAwarenessScore;
import com.sap.sgs.phosphor.fosstars.model.score.oss.ProjectSecurityTestingScore;
import com.sap.sgs.phosphor.fosstars.model.score.oss.UnpatchedVulnerabilitiesScore;
import com.sap.sgs.phosphor.fosstars.model.score.oss.VulnerabilityLifetimeScore;
import com.sap.sgs.phosphor.fosstars.model.value.RatingValue;
import com.sap.sgs.phosphor.fosstars.model.value.ScoreValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The class print a pretty rating value.
 */
public class PrettyPrinter {

  private static final String INDENT_STEP = "  ";

  /**
   * Maps a class of feature to its shorter name which should be used in output.
   */
  private static final Map<Class, String> FEATURE_CLASS_TO_NAME = new HashMap<>();

  static {
    FEATURE_CLASS_TO_NAME.put(OssSecurityScore.class, "Security of project");
    FEATURE_CLASS_TO_NAME.put(CommunityCommitmentScore.class, "Community commitment");
    FEATURE_CLASS_TO_NAME.put(ProjectActivityScore.class, "Project activity");
    FEATURE_CLASS_TO_NAME.put(ProjectPopularityScore.class, "Project popularity");
    FEATURE_CLASS_TO_NAME.put(ProjectSecurityAwarenessScore.class, "Security awareness");
    FEATURE_CLASS_TO_NAME.put(ProjectSecurityTestingScore.class, "Security testing");
    FEATURE_CLASS_TO_NAME.put(UnpatchedVulnerabilitiesScore.class, "Unpatched vulnerabilities");
    FEATURE_CLASS_TO_NAME.put(VulnerabilityLifetimeScore.class, "Vulnerability lifetime");
  }

  /**
   * Maps a feature to its shorter name which should be used in output.
   */
  private static final Map<Feature, String> FEATURE_TO_NAME = new HashMap<>();

  static {
    FEATURE_TO_NAME.put(OssFeatures.HAS_SECURITY_TEAM, "Does it have a security team?");
    FEATURE_TO_NAME.put(OssFeatures.HAS_SECURITY_POLICY, "Does it have a security policy?");
    FEATURE_TO_NAME.put(
        OssFeatures.USES_VERIFIED_SIGNED_COMMITS, "Does it use verified signed commits?");
    FEATURE_TO_NAME.put(OssFeatures.VULNERABILITIES, "Info about vulnerabilities");
    FEATURE_TO_NAME.put(OssFeatures.SECURITY_REVIEWS_DONE, "Security reviews");
    FEATURE_TO_NAME.put(OssFeatures.IS_APACHE, "Does it belong to Apache?");
    FEATURE_TO_NAME.put(OssFeatures.IS_ECLIPSE, "Does it belong to Eclipse?");
    FEATURE_TO_NAME.put(OssFeatures.SUPPORTED_BY_COMPANY, "Is it supported by a company?");
    FEATURE_TO_NAME.put(
        OssFeatures.SCANS_FOR_VULNERABLE_DEPENDENCIES, "Does it scan for vulnerable dependencies?");
  }

  /**
   * Print out a formatted rating value.
   *
   * @param ratingValue The rating value to be printed.
   * @return A string to be displayed.
   */
  public String print(RatingValue ratingValue) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("[+] Here is how the rating was calculated:%n"));
    sb.append(print(ratingValue.scoreValue(), INDENT_STEP, true));
    sb.append(String.format("[+] Rating: %2.2f out of %2.2f -> %s%n",
        ratingValue.score(), Score.MAX, ratingValue.label()));
    sb.append(String.format("[+] Confidence: %2.2f out of %2.2f%n",
        ratingValue.confidence(), Confidence.MAX));
    return sb.toString();
  }

  /**
   * Print out a formatted score value with a specified indent.
   *
   * @param scoreValue The score value to be printed.
   * @param indent The indent.
   * @return A string to be displayed.
   */
  private String print(ScoreValue scoreValue, String indent, boolean isMainScore) {
    StringBuilder sb = new StringBuilder();

    if (!isMainScore) {
      sb.append(String.format("[+] %sSub-score:....%s%n", indent, nameOf(scoreValue.score())));
    } else {
      sb.append(String.format("[+] %sScore:........%s%n", indent, nameOf(scoreValue.score())));
    }

    if (!scoreValue.score().description().isEmpty()) {
      String[] lines = scoreValue.score().description().split("\n");
      sb.append(String.format("[+] %sDescription:..%s%n", indent, lines[0]));
      for (int i = 1; i < lines.length; i++) {
        sb.append(String.format("[+] %s              %s%n", indent, lines[i]));
      }
    }

    if (!isMainScore) {
      sb.append(String.format("[+] %sImportance:...%s (weight %2.2f out of %2.2f)%n",
          indent, importance(scoreValue.weight()), scoreValue.weight(), Weight.MAX));
    }

    sb.append(String.format("[+] %sValue:........%s out of %2.2f%n",
        indent,
        append(String.format("%.2f", scoreValue.get()), ' ', 4),
        Score.MAX));
    sb.append(String.format("[+] %sConfidence:...%s out of %2.2f%n",
        indent,
        append(String.format("%.2f", scoreValue.confidence()), ' ', 4),
        Confidence.MAX));

    List<ScoreValue> subScoreValues = new ArrayList<>();
    List<Value> featureValues = new ArrayList<>();
    for (Value usedValue : scoreValue.usedValues()) {
      if (usedValue instanceof ScoreValue) {
        subScoreValues.add((ScoreValue) usedValue);
      } else {
        featureValues.add(usedValue);
      }
    }

    if (!subScoreValues.isEmpty()) {

      // sort the sub-score values by their importance
      subScoreValues.sort(Collections.reverseOrder(Comparator.comparingDouble(ScoreValue::weight)));

      sb.append(String.format(
          "[+] %sBased on:.....%d sub-scores:%n", indent, subScoreValues.size()));
      for (ScoreValue usedValue : subScoreValues) {
        sb.append(print(usedValue, indent + INDENT_STEP + INDENT_STEP, false));
        sb.append("[+]\n");
      }
    }

    if (!featureValues.isEmpty()) {
      sb.append(String.format("[+] %sBased on:...%d features:%n", indent, featureValues.size()));
      Map<String, Object> nameToValue = new TreeMap<>(String::compareTo);
      int maxLength = 0;
      for (Value usedValue : featureValues) {
        String name = nameOf(usedValue.feature());
        nameToValue.put(name, usedValue.isUnknown() ? "unknown" : usedValue.get());
        if (maxLength < name.length()) {
          maxLength = name.length();
        }
      }
      for (Map.Entry<String, Object> entry : nameToValue.entrySet()) {
        String name = entry.getKey();
        name += name.endsWith("?") ? "." : ":";
        sb.append(String.format("[+] %s  %s%s%n",
            indent + INDENT_STEP,
            append(name, '.', maxLength + 3),
            entry.getValue()));
      }
    }

    if (!scoreValue.explanation().isEmpty()) {
      Iterator<String> iterator = scoreValue.explanation().iterator();
      sb.append(String.format("[+] %sExplanation:..%s%n", indent, iterator.next()));
      while (iterator.hasNext()) {
        sb.append(String.format("[+] %s              %s%n", indent, iterator.next()));
      }
    }

    return sb.toString();
  }

  /**
   * Adds a number of specified characters to the end of a string
   * to make it fit to the specified length.
   *
   * @param string The original string.
   * @param c The character to be appended.
   * @param length The final length of the string.
   * @return A string with appended characters
   *         if the length of the original string is less than the specified length,
   *         otherwise the original string.
   */
  private static String append(String string, char c, int length) {
    StringBuilder sb = new StringBuilder(string);
    while (sb.length() <= length) {
      sb.append(c);
    }
    return sb.toString();
  }

  /**
   * Figures out how a name of a feature should be printed out.
   *
   * @param feature The feature.
   * @return A name of the feature.
   */
  static String nameOf(Feature feature) {
    for (Map.Entry<Class, String> entry : FEATURE_CLASS_TO_NAME.entrySet()) {
      if (feature.getClass() == entry.getKey()) {
        return entry.getValue();
      }
    }
    for (Map.Entry<Feature, String> entry : FEATURE_TO_NAME.entrySet()) {
      if (feature.equals(entry.getKey())) {
        return entry.getValue();
      }
    }
    return feature.name();
  }

  /**
   * Returns a human-readable label for a weight.
   *
   * @param weight The weight.
   * @return A human-readable label.
   */
  private static String importance(double weight) {
    if (weight < 0.3) {
      return "Low";
    }
    if (weight < 0.66) {
      return "Medium";
    }
    return "High";
  }

}