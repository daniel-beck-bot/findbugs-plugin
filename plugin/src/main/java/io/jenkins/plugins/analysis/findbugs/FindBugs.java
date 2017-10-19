package io.jenkins.plugins.analysis.findbugs;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import io.jenkins.plugins.analysis.core.steps.StaticAnalysisTool;

import hudson.Extension;
import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.findbugs.FindBugsDescriptor;
import hudson.plugins.findbugs.Messages;
import hudson.plugins.findbugs.parser.FindBugsParser;

/**
 * Provides a parser and customized messages for FindBugs.
 *
 * @author Ullrich Hafner
 */
public class FindBugs extends StaticAnalysisTool {
    // FIXME: enum and not boolean
    private boolean useRankAsPriority;

    /**
     * Creates a new instance of {@link FindBugs}.
     */
    @DataBoundConstructor
    public FindBugs() {
        // empty constructor required for stapler
    }

    /**
     * If useRankAsPriority is {@code true}, then the FindBugs parser will use the rank when evaluation the priority.
     * Otherwise the priority of the FindBugs warning will be mapped.
     *
     * @param useRankAsPriority
     *         {@code true} to use the rank, {@code false} to use the
     */
    @DataBoundSetter
    public void setUseRankAsPriority(final boolean useRankAsPriority) {
        this.useRankAsPriority = useRankAsPriority;
    }

    public boolean getUseRankAsPriority() {
        return useRankAsPriority;
    }

    @Override
    public Collection<FileAnnotation> parse(final File file, final String moduleName) throws InvocationTargetException {
        return new FindBugsParser(useRankAsPriority).parse(file, moduleName);
    }

    /** Registers this tool as extension point implementation. */
    @Extension
    public static final class Descriptor extends StaticAnalysisToolDescriptor {
        public Descriptor() {
            super(FindBugsDescriptor.PLUGIN_ID);
        }

        @Override
        public String getName() {
            return "FindBugs";
        }

        @Override
        public String getLinkName() {
            return Messages.FindBugs_ProjectAction_Name();
        }

        @Override
        public String getTrendName() {
            return Messages.FindBugs_Trend_Name();
        }

        @Override
        public String getSmallIconUrl() {
            return get().getIconUrl();
        }

        private FindBugsDescriptor get() {
            return new FindBugsDescriptor();
        }

        @Override
        public String getLargeIconUrl() {
            return get().getSummaryIconUrl();
        }
    }
}
