package com.aicodereview.platform.analysis;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.CatchClause;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodeAnalyzerService {

    @Autowired
    private ScoringService scoringService;

    public AnalysisResult analyze(String code) {
        List<AnalysisIssue> issues = new ArrayList<>();

        try {
            CompilationUnit cu = StaticJavaParser.parse(code);

            checkUnusedImports(cu, issues);
            checkEmptyCatchBlocks(cu, issues);
            checkLongMethods(cu, issues);
            checkNamingConvention(cu, issues);
            checkHardcodedSecrets(cu, issues);

        } catch (ParseProblemException e) {
            issues.add(new AnalysisIssue("SYNTAX_ERROR",
                "Code does not compile: " + e.getMessage(), 0, "HIGH"));
        }

        int score = scoringService.calculateScore(issues);
        return new AnalysisResult(issues, issues.size(), score);
    }

    private void checkUnusedImports(CompilationUnit cu, List<AnalysisIssue> issues) {
        for (ImportDeclaration imp : cu.getImports()) {
            String simpleName = imp.getNameAsString();
            String className = simpleName.substring(simpleName.lastIndexOf('.') + 1);
            long occurrences = cu.toString().split(className, -1).length - 1;
            if (occurrences <= 1) {
                issues.add(new AnalysisIssue("UNUSED_IMPORT",
                    "Unused import: " + simpleName,
                    imp.getBegin().map(p -> p.line).orElse(0),
                    "LOW"));
            }
        }
    }

    private void checkEmptyCatchBlocks(CompilationUnit cu, List<AnalysisIssue> issues) {
        cu.findAll(CatchClause.class).forEach(catchClause -> {
            if (catchClause.getBody().getStatements().isEmpty()) {
                issues.add(new AnalysisIssue("EMPTY_CATCH",
                    "Empty catch block",
                    catchClause.getBegin().map(p -> p.line).orElse(0),
                    "MEDIUM"));
            }
        });
    }

    private void checkLongMethods(CompilationUnit cu, List<AnalysisIssue> issues) {
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            method.getBody().ifPresent(body -> {
                int lineCount = body.getEnd().map(e -> e.line).orElse(0)
                        - body.getBegin().map(b -> b.line).orElse(0);
                if (lineCount > 40) {
                    issues.add(new AnalysisIssue("LONG_METHOD",
                        "Method '" + method.getNameAsString() + "' is too long (" + lineCount + " lines)",
                        method.getBegin().map(p -> p.line).orElse(0),
                        "MEDIUM"));
                }
            });
        });
    }

    private void checkNamingConvention(CompilationUnit cu, List<AnalysisIssue> issues) {
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            String name = method.getNameAsString();
            if (!name.matches("^[a-z][a-zA-Z0-9]*$")) {
                issues.add(new AnalysisIssue("NAMING_CONVENTION",
                    "Method name '" + name + "' should be camelCase",
                    method.getBegin().map(p -> p.line).orElse(0),
                    "LOW"));
            }
        });
    }

    private void checkHardcodedSecrets(CompilationUnit cu, List<AnalysisIssue> issues) {
        cu.findAll(VariableDeclarator.class).forEach(var -> {
            String varName = var.getNameAsString().toLowerCase();
            boolean looksLikeSecret = varName.contains("password")
                    || varName.contains("secret")
                    || varName.contains("apikey");
            if (looksLikeSecret
                    && var.getInitializer().isPresent()
                    && var.getInitializer().get().isStringLiteralExpr()) {
                issues.add(new AnalysisIssue("HARDCODED_SECRET",
                    "Possible hardcoded secret in variable '" + var.getNameAsString() + "'",
                    var.getBegin().map(p -> p.line).orElse(0),
                    "HIGH"));
            }
        });
    }
}