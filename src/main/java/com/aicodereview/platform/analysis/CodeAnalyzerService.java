package com.aicodereview.platform.analysis;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.stmt.CatchClause;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodeAnalyzerService {

    public AnalysisResult analyze(String code) {
        List<AnalysisIssue> issues = new ArrayList<>();

        try {
            CompilationUnit cu = StaticJavaParser.parse(code);

            for (ImportDeclaration imp : cu.getImports()) {
                String simpleName = imp.getNameAsString();
                String className = simpleName.substring(simpleName.lastIndexOf('.') + 1);
                long occurrences = cu.toString().split(className, -1).length - 1;
                if (occurrences <= 1) {
                    issues.add(new AnalysisIssue("UNUSED_IMPORT",
                        "Unused import: " + simpleName,
                        imp.getBegin().map(p -> p.line).orElse(0)));
                }
            }

            cu.findAll(CatchClause.class).forEach(catchClause -> {
                if (catchClause.getBody().getStatements().isEmpty()) {
                    issues.add(new AnalysisIssue("EMPTY_CATCH",
                        "Empty catch block",
                        catchClause.getBegin().map(p -> p.line).orElse(0)));
                }
            });

        } catch (ParseProblemException e) {
            issues.add(new AnalysisIssue("SYNTAX_ERROR",
                "Code does not compile: " + e.getMessage(), 0));
        }

        return new AnalysisResult(issues, issues.size());
    }
}