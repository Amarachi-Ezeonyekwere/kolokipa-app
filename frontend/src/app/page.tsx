import Link from "next/link";
import Image from "next/image";
import { Button } from "@/components/ui/button";
import { KoloLogo } from "@/components/kolo-logo";

const QUOTES = [
  { text: "A woman who saves alone builds a room. A woman who saves with her sisters builds a house.", attribution: "West African proverb" },
  { text: "What one hand cannot lift, many hands carry with ease.", attribution: "Igbo proverb" },
  { text: "The wealth of a woman is counted in the circles she keeps.", attribution: "KoloKipa" },
];

export default function LandingPage() {
  return (
    <main className="min-h-screen bg-background overflow-hidden">
      <nav className="flex items-center justify-between px-6 py-6 sm:px-12 relative z-10">
        <div className="flex items-center gap-2">
            <KoloLogo size={32} />
        <span className="text-2xl font-display">KoloKipa</span>
        </div>
        <div className="flex gap-3">
          <Link href="/login">
            <Button variant="outline">Sign in</Button>
          </Link>
          <Link href="/register">
            <Button>Start a Circle</Button>
          </Link>
        </div>
      </nav>

      {/* HERO — asymmetric, photo + ring motif */}
      <section className="relative px-6 sm:px-12 pt-12 pb-24 sm:pt-16 sm:pb-32">
        <div className="mx-auto max-w-6xl grid grid-cols-1 lg:grid-cols-2 gap-16 items-center">
          <div className="relative z-10 order-2 lg:order-1">
            <p className="text-sm tracking-widest uppercase text-accent mb-4 font-medium">
              Esusu · Chama · Stokvel · Tontine
            </p>
            <h1 className="text-5xl sm:text-6xl font-display leading-[1.1] mb-6 text-foreground">
              You are the wealth
              <br />
              your circle was built on.
            </h1>
            <p className="text-lg text-muted-foreground mb-10 max-w-md leading-relaxed">
              Generations of women have pooled what they had to build what
              they dreamed of. KoloKipa keeps that tradition honest,
              transparent, and unmistakably yours.
            </p>
            <Link href="/register">
              <Button size="lg" className="text-base px-8 py-6">
                Start Your Circle Today
              </Button>
            </Link>
          </div>

          <div className="relative order-1 lg:order-2 h-[420px] sm:h-[520px] rounded-2xl overflow-hidden">
            <Image
              src="/images/hero-woman.jpg"
              alt="A woman building her legacy through community savings"
              fill
              className="object-cover"
              priority
            />
            <div className="absolute inset-0 bg-gradient-to-t from-primary/40 via-transparent to-transparent" />
          </div>
        </div>
      </section>

      {/* QUOTE STRIP — warm, textured */}
      <section className="relative px-6 sm:px-12 py-16 bg-primary text-background">
        <div className="max-w-3xl mx-auto text-center">
          <p className="text-2xl sm:text-3xl font-display leading-snug mb-4">
            &ldquo;{QUOTES[0].text}&rdquo;
          </p>
          <p className="text-sm tracking-widest uppercase opacity-70">
            {QUOTES[0].attribution}
          </p>
        </div>
      </section>

      {/* VALUES — Trust / Prosperity / Sisterhood */}
      <section className="relative px-6 sm:px-12 py-20">
        <div className="max-w-4xl mx-auto grid grid-cols-1 sm:grid-cols-3 gap-10">
          <div>
            <p className="text-2xl font-display text-primary mb-3">Trust</p>
            <p className="text-muted-foreground leading-relaxed">
              Every contribution recorded. Every collector, fairly chosen.
              No confusion, no disputes.
            </p>
          </div>
          <div>
            <p className="text-2xl font-display text-primary mb-3">Prosperity</p>
            <p className="text-muted-foreground leading-relaxed">
              This is how generations of women have built wealth. We just
              made it impossible to lose track of.
            </p>
          </div>
          <div>
            <p className="text-2xl font-display text-primary mb-3">Sisterhood</p>
            <p className="text-muted-foreground leading-relaxed">
              Your circle is your community. KoloKipa is simply here to
              honor what you&apos;ve already built.
            </p>
          </div>
        </div>
      </section>

      {/* COMMUNITY — two photos, more warmth */}
      <section className="relative px-6 sm:px-12 py-20 bg-card border-y border-border">
        <div className="max-w-5xl mx-auto">
          <h2 className="text-3xl font-display text-center mb-4">
            Built on a tradition older than any app.
          </h2>
          <p className="text-muted-foreground text-center max-w-xl mx-auto mb-12">
            From market stalls to living rooms, women have always found a
            way to build together. We&apos;re just here to keep the record straight.
          </p>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
            <div className="relative h-72 rounded-xl overflow-hidden">
              <Image
                src="/images/community-1.jpg"
                alt="Women in a savings circle"
                fill
                className="object-cover"
              />
            </div>
            <div className="relative h-72 rounded-xl overflow-hidden">
              <Image
                src="/images/community-2.jpg"
                alt="A woman celebrating her contribution"
                fill
                className="object-cover"
              />
            </div>
          </div>
        </div>
      </section>

      {/* SECOND QUOTE + FINAL CTA */}
      <section className="relative px-6 sm:px-12 py-24 text-center">
        <p className="text-xl sm:text-2xl font-display max-w-2xl mx-auto mb-3 text-foreground">
          &ldquo;{QUOTES[1].text}&rdquo;
        </p>
        <p className="text-sm tracking-widest uppercase text-muted-foreground mb-12">
          {QUOTES[1].attribution}
        </p>
        <Link href="/register">
          <Button size="lg" className="text-base px-8 py-6">
            Begin Your Legacy
          </Button>
        </Link>
      </section>

      <footer className="px-6 sm:px-12 py-10 text-center text-sm text-muted-foreground border-t border-border">
        KoloKipa — Traditional savings. Modern accountability.
      </footer>
    </main>
  );
}