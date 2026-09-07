package com.aicodereview.platform.analysis;

import com.aicodereview.platform.submission.Submission;
import com.aicodereview.platform.submission.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private CodeAnalyzerService analyzerService;

    @Autowired
    private SubmissionRepository submissionRepository;

    @PostMapping("/{submissionId}")
    public AnalysisResult analyzeSubmission(@PathVariable Long submissionId) {
        Submission submission = submissionRepository.findById(submissionId)
            .orElseThrow(() -> new RuntimeException("Submission not found"));
        return analyzerService.analyze(submission.getCode());
    }
}
