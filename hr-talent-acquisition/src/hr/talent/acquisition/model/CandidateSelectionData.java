package hr.talent.acquisition.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data class for the Candidate Selection dialog.
 * Manages the state of candidate selection for approved positions.
 */
public class CandidateSelectionData {

    private List<Position> positions;
    private Map<String, List<CandidateMatch>> positionCandidateMap;
    private Map<String, List<String>> selections;
    private Map<String, List<String>> rejections;
    private CandidateProfile selectedCandidate;
    private Position currentPosition;

    public CandidateSelectionData() {
        this.selections = new HashMap<>();
        this.rejections = new HashMap<>();
    }

    // Getters and setters

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public Map<String, List<CandidateMatch>> getPositionCandidateMap() {
        return positionCandidateMap;
    }

    public void setPositionCandidateMap(Map<String, List<CandidateMatch>> positionCandidateMap) {
        this.positionCandidateMap = positionCandidateMap;
    }

    public Map<String, List<String>> getSelections() {
        return selections;
    }

    public void setSelections(Map<String, List<String>> selections) {
        this.selections = selections;
    }

    public Map<String, List<String>> getRejections() {
        return rejections;
    }

    public void setRejections(Map<String, List<String>> rejections) {
        this.rejections = rejections;
    }

    public CandidateProfile getSelectedCandidate() {
        return selectedCandidate;
    }

    public void setSelectedCandidate(CandidateProfile selectedCandidate) {
        this.selectedCandidate = selectedCandidate;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }
}
