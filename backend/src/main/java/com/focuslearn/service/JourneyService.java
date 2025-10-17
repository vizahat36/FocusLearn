package com.focuslearn.service;

import com.focuslearn.dto.JourneyDtos;
import com.focuslearn.model.Journey;
import com.focuslearn.model.Step;
import com.focuslearn.repository.JourneyRepository;
import com.focuslearn.repository.StepRepository;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JourneyService {

    private final JourneyRepository journeyRepository;
    private final StepRepository stepRepository;

    public JourneyService(JourneyRepository journeyRepository, StepRepository stepRepository) {
        this.journeyRepository = journeyRepository;
        this.stepRepository = stepRepository;
    }

    public Journey create(JourneyDtos.JourneyCreateRequest req, UUID ownerId) {
        Journey j = new Journey();
        j.setTitle(req.title);
        j.setDescription(req.description);
        j.setOwnerId(ownerId);
        return journeyRepository.save(j);
    }

    public Optional<Journey> get(UUID id) {
        return journeyRepository.findById(id);
    }

    public List<Journey> list() {
        return journeyRepository.findAll();
    }

    public Journey update(UUID id, JourneyDtos.JourneyUpdateRequest req) {
        Journey j = journeyRepository.findById(id).orElseThrow();
        j.setTitle(req.title);
        j.setDescription(req.description);
        return journeyRepository.save(j);
    }

    public void delete(UUID id) {
        journeyRepository.deleteById(id);
    }

    // naive import: accept youtube playlist URL and extract video IDs (mock)
    public Journey importFromPlaylist(UUID ownerId, String playlistUrl, String title) throws Exception {
        // Parse the URL and find video IDs - in production use YouTube Data API
        URL u = new URL(playlistUrl);
        String q = u.getQuery();
        Journey j = new Journey();
        j.setTitle(title != null ? title : "Imported playlist");
        j.setOwnerId(ownerId);
        journeyRepository.save(j);

        // very naive: if 'list' param present, create dummy steps; otherwise try 'v' param
        if (q != null && q.contains("list=")) {
            // create a few placeholder steps (demo)
            for (int i = 1; i <= 5; i++) {
                Step s = new Step();
                s.setJourney(j);
                s.setTitle("Imported video " + i);
                s.setType("VIDEO");
                s.setVideoId("video" + i);
                s.setPositionIndex(i - 1);
                stepRepository.save(s);
            }
        } else {
            // single video id
            Step s = new Step();
            s.setJourney(j);
            s.setTitle("Imported video");
            s.setType("VIDEO");
            s.setVideoId("video-single");
            s.setPositionIndex(0);
            stepRepository.save(s);
        }

        return j;
    }

    public void addStep(UUID journeyId, com.focuslearn.dto.JourneyDtos.StepSummary stepSummary) {
        Journey j = journeyRepository.findById(journeyId).orElseThrow();
        Step s = new Step();
        s.setJourney(j);
        s.setTitle(stepSummary.title);
        s.setType(stepSummary.type);
        s.setPositionIndex(stepSummary.positionIndex != null ? stepSummary.positionIndex : j.getSteps().size());
        stepRepository.save(s);
    }

    public void deleteStep(UUID journeyId, UUID stepId) {
        // simple delete by id
        stepRepository.deleteById(stepId);
    }

    public Journey fork(UUID existingJourneyId, UUID newOwnerId) {
        Journey src = journeyRepository.findById(existingJourneyId).orElseThrow();
        Journey fork = new Journey();
        fork.setTitle(src.getTitle() + " (fork)");
        fork.setDescription(src.getDescription());
        fork.setOwnerId(newOwnerId);
        fork.setSourceJourneyId(src.getId());
        journeyRepository.save(fork);

        // copy steps
        List<Step> newSteps = src.getSteps().stream().map(s -> {
            Step ns = new Step();
            ns.setJourney(fork);
            ns.setTitle(s.getTitle());
            ns.setType(s.getType());
            ns.setVideoId(s.getVideoId());
            ns.setVideoUrl(s.getVideoUrl());
            ns.setPositionIndex(s.getPositionIndex());
            stepRepository.save(ns);
            return ns;
        }).collect(Collectors.toList());
        fork.setSteps(newSteps);
        return journeyRepository.save(fork);
    }
}
