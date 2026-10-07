import { html } from "lit";
import "@carbon/web-components/es/components/button/index.js";
import "@carbon/web-components/es/components/text-input/index.js";
import "@carbon/web-components/es/components/tile/index.js";
import "../../src/styles/brand-theme.css";
import "../../src/styles/carbon-page-layout.css";

// Documents the shape tokens in brand-theme.css (--radius-small/--radius-large). They mirror
// the Radius collection in the Carbon Design System Figma library (Radius/Small = 4,
// Radius/Large = 12). Keep this list in sync with both - it is a visual reference, not a
// source of truth.
const RADII = [
  ["Small", "--radius-small", "0.25rem", 4, "Buttons, dropdown triggers, text/number/select inputs"],
  ["Large", "--radius-large", "0.75rem", 12, "Cards and modals"],
];

const swatch = ([name, token, rem, px, usage]) => html`
  <div class="token-swatch">
    <div
      class="token-swatch-color"
      style="width: 6rem; height: 4rem; background: var(--cds-layer-01); border: 1px solid var(--cds-border-strong); border-radius: var(${token});"
    ></div>
    <code class="token-swatch-code">${token}</code>
    <span class="token-swatch-value">${name} - ${rem} (${px}px)</span>
    <span class="token-swatch-value">${usage}</span>
  </div>
`;

export default {
  title: "Foundations/Border Radius",
  render: () => html`
    <div data-carbon-theme="brand" class="cds--white" style="display: flex; flex-direction: column; gap: 2rem;">
      <section class="page-section">
        <h3 class="page-section-heading">Radius tokens</h3>
        <div style="display: flex; gap: 2rem; flex-wrap: wrap;">${RADII.map(swatch)}</div>
      </section>
      <section class="page-section">
        <h3 class="page-section-heading">In use</h3>
        <div style="display: flex; gap: 1rem; align-items: flex-start; flex-wrap: wrap;">
          <cds-button>Small radius button</cds-button>
          <cds-text-input label="Small radius input" placeholder="Placeholder"></cds-text-input>
          <div style="width: 16rem; padding: 1rem; background: var(--cds-background); border: 1px solid var(--cds-border-subtle); border-radius: var(--radius-large);">
            Large radius card
          </div>
        </div>
      </section>
    </div>
  `,
};

export const Default = {};
