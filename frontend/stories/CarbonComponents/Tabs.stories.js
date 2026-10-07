import "@carbon/web-components/es/components/tabs/index.js";
import { html } from "lit";

// Line tabs, medium (Gifts frame 1287:1375, Tabs 1287:6514). Selected = semibold with a
// 2px --cds-border-interactive underline; unselected = --cds-text-secondary with a 2px
// --cds-border-subtle underline. Both come straight from Carbon's own tab styles reading the
// brand tokens, so there is no tab-specific CSS in the project.
const TABS = ["All", "Still Needed", "Purchased", "Stored", "Given"];

export default {
  title: "Carbon Components/Tabs",
  render: ({ size }) => html`
    <cds-tabs value="all" size=${size}>
      ${TABS.map(
        (label) => html`<cds-tab id="tab-${label}" value=${label.toLowerCase().replace(/ /g, "-")}>${label}</cds-tab>`
      )}
    </cds-tabs>
  `,
  argTypes: {
    size: { control: "select", options: ["md", "lg"] },
  },
  args: { size: "md" },
};

export const Default = {};
