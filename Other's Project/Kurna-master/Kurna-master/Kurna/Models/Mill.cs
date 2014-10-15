using System;

namespace Kurna.Models
{
    public class Mill : IEquatable<Mill>
    {
        public Tile First { get; set; }
        public Tile Second { get; set; }
        public Tile Third { get; set; }
        public int Turn { get; set; }

        public Mill(Tile first, Tile second, Tile third)
        {
            First = first;
            Second = second;
            Third = third;
        }

        public bool Equals(Mill other)
        {
            return First == other.First &&
                   Second == other.Second &&
                   Third == other.Third;
        }
    }
}